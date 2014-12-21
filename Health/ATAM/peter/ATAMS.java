import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Random;
import org.apache.commons.math.special.Gamma;

public class ATAMS extends TopicModel {

	public HashMap<String,Integer> wordMap;
	public HashMap<Integer,String> wordMapInv;

	public int[][] docs;
	public String[] docsC;
	public int[] docsA;
	public int[][] docsZ;
	public int[][] docsY;
	public int[][] docsY0;
	public int[][] docsL;
	public int[][] docsX;
	public int[][] docsB;

	public int[] nA;
	public int[][] nDZ;
	public int[] nD;
	public int[][] nDY;
	public int[][] nDL;
	public int[][] nZW;
	public int[][][] nZWY;
	public int[][] nTW;
	public int[] nT;
	public int[][] nWY;
	public int[] nY;
	public int[] nZ;
	public int[][] nZY;
	public int[][][] nLZX;
	public int[][] nLZ;
	public int[] nBW;
	public int nB;
	public int[][] nBWY;
	public int[] nBY;

	public int D;
	public int W;
	public int Y;
	public int Z;
	public int A;
	public int A0;
	
	public double beta;
	public double[][] alpha;
	public double[] alphaSum;
	public double[][] prior;
	public double[][] priorP;
	public double[] priorNorm;
	public double omega;
	public double gamma0;
	public double gamma1;
	public double delta0;
	public double delta1;
	public double labelPrior;
	
	public ATAMS(int z, int y, int ail, double a, double b, double w, double g0, double g1, 
				double d0, double d1, double lp) {
		A = ail;
		Z = z;
		Y = y;
		alpha = new double[A][Z];
		alphaSum = new double[A];
		for (int i = 0; i < A; i++) {
			double sum = 0;
			for (int j = 0; j < Z; j++) {
				alpha[i][j] = a;
				sum += a;
			}
			alphaSum[i] = sum;
		}
		beta = b;
		omega = w;
		gamma0 = g0;
		gamma1 = g1;
		delta0 = d0;
		delta1 = d1;
		labelPrior = lp;
		if (labelPrior == 0) labelPrior = beta;
	}
	
	public void initialize() {
		System.out.println("Initializing...");
		Random r = new Random();

		docsA = new int[D];
		docsZ = new int[D][];
		//docsY = new int[D][];
		docsL = new int[D][];
		docsX = new int[D][];
		docsB = new int[D][];

		nA = new int[A];
		nDZ = new int[D][Z];
		nD = new int[D];
		nDY = new int[D][Z];
		nDL = new int[D][2];
		nZW = new int[A][W];
		nZWY = new int[A][W][Y+1];
		nTW = new int[Z][W];
		nT = new int[Z];
		nWY = new int[W][Y+1];
		nY = new int[Y];
		nZ = new int[A];
		nZY = new int[A][Y+1];
		nLZX = new int[2][A][2];
		nLZ = new int[2][A];
		nBW = new int[W];
		nB = 0;		
		nBWY = new int[Y+1][W];
		nBY = new int[Y+1];		

		for (int d = 0; d < D; d++) { 
			docsZ[d] = new int[docs[d].length];
			docsY[d] = new int[docs[d].length];
			docsL[d] = new int[docs[d].length];
			docsX[d] = new int[docs[d].length];
			docsB[d] = new int[docs[d].length];
			
			int a = r.nextInt(A);
			docsA[d] = a;
			nA[a] += 1;

			for (int n = 0; n < docs[d].length; n++) {
				int w = docs[d][n];


				int z = r.nextInt(Z);		// select random z value in {0...Z-1}
				docsZ[d][n] = z;
				//int y = r.nextInt(Y);		// select random y value in {0...Y-1}
				//docsY[d][n] = y;		
	
				int y0 = docsY0[d][n];

				/*if (y0 != -1) {
					nBWY[y0][w] += 1;
					nBY[y0] += 1;
				} else {
					nBW[w] += 1;
					nB += 1;
				}*/

				//int y = r.nextInt(Y);	
				//if (y0 != -1) y = y0;
				//
				docsY[d][n] = y0;

				int l = r.nextInt(2);		// select random l value in {0,1}
				if (y0 != -1) l = 1;
				docsL[d][n] = l;

				//int x = r.nextInt(2);		// select random x value in {0,1}
				int x = 0;
				if (y0 != -1) x = 1;
				docsX[d][n] = x;

				int b = r.nextInt(2);
				docsB[d][n] = b;			
	
				// update counts

				if (b == 1) {
				nDL[d][l] += 1;
				int z0 = (l == 0) ? 0 : a;
				nLZX[l][z0][x] += 1;
				nLZ[l][z0] += 1;
				
				if (l == 0 ) {
					nDZ[d][z] += 1;
					nD[d] += 1;
					nTW[z][w] += 1;
					nT[z] += 1;
				}			
				else if (l == 1) {
					nZWY[a][w][y0+1] += 1;	
					nZY[a][y0+1] += 1;				
				}
				}
				else { //b=0
					//if (y0 != -1) {
						nBWY[y0+1][w] += 1;
						nBY[y0+1] += 1;
					//} else {
					//	nBW[w] += 1;
					//	nB += 1;
					//}
				}
			}
		}

	}
	
	public void doSampling(int iter) {
		double[][] alpha0 = new double[A][Z];
		double[] alpha0D = new double[A];
		// these two are new
		double[][] alpha02 = new double[A][Z];
		double[] alphaTotal = new double[A];

		for (int d = 0; d < D; d++) {
			sampleD(d);
			for (int n = 0; n < docs[d].length; n++) {
				sample(d, n);
			}

			int a = docsA[d];
			for (int z = 0; z < Z; z++) {
				if (nD[d] == 0) continue;
				double p = (nDZ[d][z] + 0.01)/ (nD[d] + 0.01*Z);
				alpha0[a][z] += p;
				alpha02[a][z] += p*p;
				alphaTotal[a] += 1.0;
			}
		}

		if (iter % 20 != 0 || iter < 200) return;


		double step = 10.0;		
		for (int i = 0; i < 3; i++) {
			for (int a = 0; a < A; a++) { //A0
				double s = priorNorm[a];
				//System.out.println(i+" a="+a+" s="+s);

				int countA = 0;
				for (int y = 0; y < 3; y++) {
					countA += nZY[a][y];
				}

				//double ds = Gamma.digamma(s);
				//double ds2 = Gamma.trigamma(s);
				double ds = 0;
				double denom = Gamma.digamma(countA+s) - Gamma.digamma(s);

				for (int w = 0; w < W; w++) {
					double m = priorP[a][w];
		
					int countAW = 0;
					for (int y = 0; y < 3; y++) {
						countAW += nZWY[a][w][y];
					}
					ds += m*Gamma.digamma(countAW+s*m);
					ds -= m*Gamma.digamma(s*m);

					//double p = (countAW+1.0) / (countA+1.0*W);

					//ds -= m*Gamma.digamma(s*m);
					//ds += m*Math.log(p);
					//ds2 -= m*m*Gamma.trigamma(s*m);
				}

				priorNorm[a] = s*(ds/denom);
				System.out.println(a+" "+priorNorm[a]);

				/*
				double sign = (ds2 >= 0) ? 1.0 : -1.0;
				//System.out.println(ds);

				double stepA = step;
				priorNorm[a] = 0;
				while (priorNorm[a] < 10.0) {
					priorNorm[a] =  s - sign*stepA*ds;
					stepA /= 10.0;	// if the value becomes less than 1, we have
							// stepped to far, so try again with a smaller
							// step size
				}*/
			}
		}
		

		for (int a = 0; a < A0; a++) {
			for (int w = 0; w < W; w++) {
				prior[a][w] = priorP[a][w] * priorNorm[a];
			}
			System.out.println("norm_"+a+": "+priorNorm[a]);
		}


		// estimate alpha
		for (int a = 0; a < A; a++) {
			alphaSum[a] = 0;
			double[] m = new double[Z];

			for (int z = 0; z < Z; z++) {
				alpha[a][z] = alpha0[a][z] / alphaTotal[a];

				alpha02[a][z] /= alphaTotal[a];
				alpha02[a][z] = alpha02[a][z] - (alpha[a][z]*alpha[a][z]);

				m[z] = (alpha[a][z] * (1.0-alpha[a][z])) / alpha02[a][z];
				m[z] -= 1.0;
			}
				
			double total = 0;
			for (int z = 0; z < Z; z++) {
				total += alpha[a][z];
				alphaSum[a] += Math.log(m[z]);
			}
			alphaSum[a] = Math.exp(alphaSum[a] / (Z-1.0));
			//alphaSum[a] /= 3.0;

			for (int z = 0; z < Z; z++) {
				alpha[a][z] = (alpha[a][z] / total) * alphaSum[a];
			}
		}

		System.out.println("alpha:");
		for (int z = 0; z < Z; z++) {
			System.out.print(z+" ");
			for (int a = 0; a < A; a++) {
				System.out.print(alpha[a][z]+" ");
			}
			System.out.println();
		}
		/*
		for (int i = 0; i < 3; i++) {
			for (int d = 0; d < D; d++) {
				int a = docsA[d];

				if (nD[d] == 0) continue;
				for (int z = 0; z < Z; z++) {
					alpha0[a][z] += Gamma.digamma(nDZ[d][z]+0.0 + alpha[a][z]) - Gamma.digamma(alpha[a][z]);
				}
				alpha0D[a] += Gamma.digamma(nD[d]+0.0*Z + alphaSum[a]) - Gamma.digamma(alphaSum[a]);
			}

			for (int a = 0; a < A; a++) {
				alphaSum[a] = 0;

				for (int z = 0; z < Z; z++) {
					if (alpha0D[a] > 0) alpha[a][z] = alpha0[a][z] / alpha0D[a];
					else alpha[a][z] = 0.01;
					alpha[a][z] += 0.01;

					if (i == 2) alpha[a][z] /= 10.0;
					alphaSum[a] += alpha[a][z];

					alpha0[a][z] = 0;

					if (i == 2) System.out.println(a+"->"+z+" "+alpha[a][z]);
				}
				alpha0D[a] = 0;
				if (i == 2) System.out.println(a+":  "+alphaSum[a]);
				if (i == 2) System.out.println("count:  "+nA[a]);
			}
		}
		*/
	}
		
	public void sampleD(int d) {
		int N = docs[d].length;
		int ailment = docsA[d];

		double pTotal = 0.0;
		double[] p = new double[A];
	
		// decrement counts

		nA[ailment] -= 1;	
		for (int n = 0; n < docs[d].length; n++) {
			int w = docs[d][n];
			int topic = docsZ[d][n];
			int y0 = docsY0[d][n];
			int level = docsL[d][n];
			int route = docsX[d][n];
			if (docsB[d][n] == 0) continue;

			if (level == 1) {
				nZWY[ailment][w][y0+1] -= 1;
				nZY[ailment][y0+1] -= 1;
			}
		}

		Random r = new Random();
		boolean doit = false;
		if (r.nextInt(1000) == 0) doit = true;

		//double alphaNorm = Z*alpha;
		double betaNorm = Y*beta;
		double omegaNorm = W*omega;

		for (int n = 0; n < docs[d].length; n++) {
			int level = docsL[d][n];
			int topic = docsZ[d][n];
			int w = docs[d][n];

			if (docsB[d][n] == 0) continue;

			int y0 = docsY0[d][n];
			int route = docsX[d][n];

			for (int a = 0; a < A; a++) {
				double pa;
				
				if (level == 0) {
					pa = (nDZ[d][topic] + alpha[a][topic]) / (nD[d] + alphaSum[a]);
				} else {
					pa = (nZWY[a][w][y0+1] + prior[a][w]) / (nZY[a][y0+1] + priorNorm[a]);
					//if (doit) System.out.print(pa+"("+nZWY[a][w][y0+1]+" "+prior[a][w]+") ");
				}
				p[a] += Math.log(pa);
			}
			//if (doit) System.out.println();
		}
		//if (doit) System.out.println("-");

		for (int a = 0; a < A; a++) {
			double pa = (nA[a] + 100.0) / ((double)D + 100.0*A); 
			p[a] += Math.log(pa);
		}


		double[] p0 = new double[A];
		p0[0] = 1.0;
		pTotal = 1.0;
		for (int a = 1; a < A; a++) {
			p0[a] = Math.exp(p[a] - p[a-1]) * p0[a-1];
			pTotal += p0[a];

			//if (doit) System.out.println(p0[a]);
		}
		//if (doit) System.out.println("---");

		double u = r.nextDouble() * pTotal;
			
		double v = 0.0;
		for (int a = 0; a < A; a++) {
			v += p0[a];
				
			if (v > u) {
				ailment = a;
				break;
			}
		}

		// increment counts

		nA[ailment] += 1;
		for (int n = 0; n < docs[d].length; n++) {
			int w = docs[d][n];
			int topic = docsZ[d][n];
			int y0 = docsY0[d][n];
			int level = docsL[d][n];
			int route = docsX[d][n];
			if (docsB[d][n] == 0) continue;

			if (level == 1) {
				nZWY[ailment][w][y0+1] += 1;
				nZY[ailment][y0+1] += 1;
			}
		}
	
		// set new assignments
	
		docsA[d] = ailment;
	}


	public void sample(int d, int n) {
		int w = docs[d][n];
		int ail = docsA[d];
		int topic = docsZ[d][n];
		int level = docsL[d][n];
		int route = docsX[d][n];
		int back = docsB[d][n];	
		int y0 = docsY0[d][n];	
	
		// decrement counts

		if (back == 1) {
			nDL[d][level] -= 1;
	
			if (level == 0 ) {
				nDZ[d][topic] -= 1;
				nD[d] -= 1;
				nTW[topic][w] -= 1;
				nT[topic] -= 1;
			}		
			else if (level == 1) {
				nZWY[ail][w][y0+1] -= 1;	
				nZY[ail][y0+1] -= 1;
			}
		}
		else { //b=0
				nBWY[y0+1][w] -= 1;
				nBY[y0+1] -= 1;
		}

		double omega0 = 0.01;
		double omegaNorm0 = W*omega0;
		//double alphaNorm = Z*alpha;
		double betaNorm = Y*beta;
		double gammaNorm = gamma0 + gamma1;
		
		double lambda0 = (y0 == -1) ? 0.8 : 0.8; // 0.7 0.9 
		double lambda1 = (y0 == -1) ? 0.2 : 0.2;

		double pTotal = 0.0;
		double[] p = new double[Z+2];

		for (int z = 0; z < Z; z++) {
			//if (y0 != -1) p[z] = 0;
			//else
			p[z] = (nDZ[d][z] + alpha[ail][z]) / (nD[d] + alphaSum[ail]) *
					(nTW[z][w] + omega0) / (nT[z] + omegaNorm0) * 
					(nDL[d][0] + delta0) * lambda1;
			pTotal += p[z];
		}

		p[Z] = (nZWY[ail][w][y0+1] + prior[ail][w]) / (nZY[ail][y0+1] + priorNorm[ail]) *
			(nDL[d][1] + delta1) * lambda1;
		pTotal += p[Z];

		//p[Z+1] = lambda0 * (nBWY[y0+1][w] + 10.0*omega) / (nBY[y0+1] + 10.0*omegaNorm);
		p[Z+1] = lambda0 * (nBWY[y0+1][w] + omega0) / (nBY[y0+1] + omegaNorm0);
		pTotal += p[Z+1];		

		Random r = new Random();
		double u = r.nextDouble() * pTotal;
		
		double v = 0;
		for (int z = 0; z < Z+2; z++) {
			v += p[z];
			
			if (v > u) {
				topic = z;
				break;
			}
		}

		if (topic == Z+1) {
			back = 0;
		}
		else if (topic == Z) {
			back = 1;
			level = 1;
		}
		else {
			back = 1;
			level = 0;
		}

		// increment counts

		if (back == 1) {
			nDL[d][level] += 1;
	
			if (level == 0) {
				nDZ[d][topic] += 1;
				nD[d] += 1;
				nTW[topic][w] += 1;
				nT[topic] += 1;
			}			
			else if (level == 1) {
				nZWY[ail][w][y0+1] += 1;	
				nZY[ail][y0+1] += 1;
			}
		}
		else { //b=0
				nBWY[y0+1][w] += 1;
				nBY[y0+1] += 1;
		}

		// set new assignments

		docsZ[d][n] = topic;
		docsL[d][n] = level;
		docsX[d][n] = route;
		docsB[d][n] = back;
	}

	
	public void readDocs(String filename) throws Exception {
		System.out.println("Reading input...");
		
		wordMap = new HashMap<String,Integer>();
		wordMapInv = new HashMap<Integer,String>();
		
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr); 

		String s;
	
		D = 0;
		int dj = 0;
		while((s = br.readLine()) != null) {
			//if (dj++ % 25 != 0) continue;
			D++;
		}

		docs = new int[D][];
		docsC = new String[D];
		docsY0 = new int[D][];
		docsY = new int[D][];

		fr = new FileReader(filename);
		br = new BufferedReader(fr); 

		int d = 0;
		int di = 0;
		while ((s = br.readLine()) != null) {
			//if (di++ % 25 != 0) continue;
			String[] tokens0 = s.split("\\s+");
			docsC[d] = tokens0[0];

			String [] tokens = new String[tokens0.length-1];
			for (int n = 1; n < tokens0.length; n++) tokens[n-1] = tokens0[n];			
			
			int N = tokens.length;
			
			docs[d] = new int[N];
			docsY[d] = new int[N];
			docsY0[d] = new int[N];

			for (int n = 0; n < N; n++) {
				String[] token = tokens[n].split(":");
				String word = token[0];
				int y0 = Integer.parseInt(token[1]);	
				int y = -1;
				if (y0 == 1) y = 0;
				else if (y0 == 2) y = 1;
	
				int key = wordMap.size();
				if (!wordMap.containsKey(word)) {
					wordMap.put(word, new Integer(key));
					wordMapInv.put(new Integer(key), word);
				}
				else {
					key = ((Integer) wordMap.get(word)).intValue();
				}
				
				docs[d][n] = key;
				docsY[d][n] = y;
				docsY0[d][n] = y;
			}
			
			d++;
		}

		br.close();
		fr.close();
		
		W = wordMap.size();

		System.out.println(D+" documents");
		System.out.println(W+" word types");

		// read in articles for prior

		prior = new double[A][W];
		priorP = new double[A][W];
		priorNorm = new double[A];

		A0 = A; 
		for (int a = 0; a < A; a++) {
			for (int w = 0; w < W; w++) {
				prior[a][w] = 0.01;
				priorP[a][w] = 1.0/W;
			}
			priorNorm[a] = 0.01*W;
		}

	}

	public void writeOutput(String filename) throws Exception {
		FileWriter fw = new FileWriter(filename+".assign");
		BufferedWriter bw = new BufferedWriter(fw); 		

		for (int d = 0; d < D; d++) {
			bw.write(docsC[d]+" "+docsA[d]+" ");
			
			for (int n = 0; n < docs[d].length; n++) {
				String word = wordMapInv.get(docs[d][n]);
				bw.write(word+":"+docsZ[d][n]+":"+docsY0[d][n]+":"+docsL[d][n]+":"+docsX[d][n]+":"+docsB[d][n]+" ");
			}
			bw.newLine();
		}
		
		bw.close();
		fw.close();
	}
}
