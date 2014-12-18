dropwizard-sample
=================

Sample Project of dropwizard

Run script- ./run.sh
or
Command---> java -jar target/projects-1.0-SNAPSHOT.jar server src/main/resources/sample.yml


NOTE: In the file named pom.xml, the following section of the code is system dependent. For some versions of java, the "source" and "target" tags take the value as 1.6, while some work with 1.7 , but for the code which runs on idtonne, the below module (version 1.8) works very well without any error (This plugin attached at the end lines of the pom.xml). The user is advised to do a google search if any error creeps in other than that of related to the above one.

           <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>2.3.2</version>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                        <compilerArgument></compilerArgument>
                    </configuration>
            </plugin>
