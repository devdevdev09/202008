$(function(){
    $("#userBtn").on("click", usersConsole);
    $("#channelBtn").on("click", channelsConsole);
    $("#testBtn").on("click", getFileContent);
});

let users = [];
let channels = [];

const usersConsole = function(){
    clearContent();
    
    list.forEach(item => {
        if(item.name == "users.json"){
            item.jsonList.forEach(user => {
                users.push(user);
                appendContent(user.real_name);
            })
        }
    });

    console.log(users);
}

const channelsConsole = function(){
    clearContent();

    list.forEach(item => {
        if(item.name == "channels.json"){
            item.jsonList.forEach(channel =>{
                channels.push(channel);
                appendContent(channel.name);;
            });
        }
    });
    console.log(channels);
}

const getFileContent = function(){
    clearContent();
    var fileName = $("#channelName").val();
    
    list.forEach(item => {
        // "test/2020-03-03.json".replace(/(test\/|.json)/g,"")
        if(item.isDirectory == false && fileNameCheck(item.name, fileName)){
            item.jsonList.forEach(msg => {
                console.log(msg.text);

                appendContent(msg.text);
            })
        }
    })
}

const fileNameCheck = function(filename1, filename2){
    return filename1.replace(/([0-9]|-|.json|\/)/g,"") == filename2;
}

const appendContent = function(content){
    $("#fileContent").append(content + "\n");
}

const clearContent = function(){
    $("#fileContent").html("");
}