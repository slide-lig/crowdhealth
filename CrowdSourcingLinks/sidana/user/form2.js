/**
 * Created by dhrumilu on 10/10/2014.
 */
//importScripts("jQuery");

$( document ).ready(function() {
    var dataSetName = $("#dataSetName").attr("value");
    $("#next").click(function(){
        clearAll()
        getNewTweets(dataSetName);
    });
    clearAll();
    getNewTweets(dataSetName);
});

function clearAll() {
    $("td").html("");
}

function getNewTweets(dataSetName) {
    var serviceUrl = "http://54.88.48.230/sidana/user/getNewTweets.php?dataSetName=" + dataSetName;
    $.ajax({
        url:serviceUrl,
        async:true,
        dataType: "json",
        success: function(data){
            fillUpFormWithNewData(data);
        },
        error: function(jqXHR,textStatus,errorThrown ) {
            alert("error while getting new tweets: [textStatus]: " + textStatus + ", [errorThrown]: " + errorThrown);
        }
    });
}

function fillUpFormWithNewData(newTweets) {
    for(var i=1 ; i<=newTweets.length;i++){
        var newTweet = newTweets[i-1];
        var uniqueTweetId = newTweet['uniqueTweetId'];
        var tweetId = newTweet['tweetId'];
        var tweetText = newTweet['tweetText'];
        var dataSetName = newTweet['dataSetName'];

        var optionName = "radio" + i;
        var option1 = getRadioElement(optionName,"General Health");
        var option2 = getRadioElement(optionName,"Personal Health");
        var option3 = getRadioElement(optionName,"unrelated");
        var option4 = getRadioElement(optionName,"ambiguous");
        var option5 = getRadioElement(optionName,"notEnglish");

        var pElement = getPElement(uniqueTweetId,dataSetName,tweetId,tweetText);
        pElement.append(getBRElement());
        pElement.append(option1);
        pElement.append(getLabel("general health"));
        pElement.append(getBRElement());

        pElement.append(option2);
        pElement.append(getLabel("personal health"));
        pElement.append(getBRElement());


        pElement.append(option3);
        pElement.append(getLabel("unrelated"));
        pElement.append(getBRElement());

        pElement.append(option4);
        pElement.append(getLabel("ambiguous"));
        pElement.append(getBRElement());

        pElement.append(option5);
        pElement.append(getLabel("notEnglish"));
        pElement.append(getBRElement());

        $("#td"+i).append(pElement);
    }
}

function postData(label,uniqueTweetId,tweetId,labelId) {
    var serviceUrl = "http://54.88.48.230/sidana/user/saveTweetLabels.php";
    $.ajax({
        url:serviceUrl,
        type: "POST",
        data : {
            'label' : label,
            'uniqueTweetId' : uniqueTweetId,
            'tweetId' : tweetId,
            'labelId' : labelId
        },
        async: true,
        dataType: "json",
        success : function(data) {
        },
        error: function(jqXHR,textStatus,errorThrown ) {
            alert("error while posting: [textStatus]: " + textStatus + ", [errorThrown]: " + errorThrown);
        }
    });
}

// UTILITIES
function getRadioElement(name,value) {
    var radioButton = $("<input>", {
        type : "radio",
        name : name,
        value : value
    });
    radioButton.click(function(event){
        var label = $(this).attr("value");
        var uniqueTweetId = $(this).parent().attr("uniqueTweetId");
        var tweetId = $(this).parent().attr("tweetId");
        var labelId = $(this).parent().attr("labelId");
        postData(label,uniqueTweetId,tweetId,labelId);
    });
    return radioButton;
}

function getLabel(text) {
    var label = $("<lable>").text(text);
    return label;
}

function getPElement(uniqueTweetId,dataSetName,tweetId,tweetText) {
    var pElement = $("<P>",{
        id : tweetId,
        tweetId : tweetId,
        dataSetName : dataSetName,
        uniqueTweetId : uniqueTweetId,
        labelId: (uniqueTweetId + "_" +Date.now())
    });
    pElement.text(tweetText);
    return pElement;
}

function getBRElement() {
    return $("<BR>");
}
