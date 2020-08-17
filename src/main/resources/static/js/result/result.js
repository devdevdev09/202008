$(function(){
    console.log(list);
    $("#userBtn").on("click", usersConsole);
    $("#channelBtn").on("click", channelsConsole);
    $("#testBtn").on("click", getFileContent);
});

let users = [];
let channels = [];

const usersConsole = function(){
    
    list.forEach(item => {
        if(item.name == "users.json"){
            item.jsonList.forEach(user => {
                users.push(user);
            })
        }
    });

    console.log(users);
}

const channelsConsole = function(){
    list.forEach(item => {
        if(item.name == "channels.json"){
            item.jsonList.forEach(channel =>{
                channels.push(channel);
            });
        }
    });
    console.log(channels);
}

const getFileContent = function(){
    $("#fileContent").html("");
    var fileName = $("#channelName").val();
    
    list.forEach(item => {
        // "test/2020-03-03.json".replace(/(test\/|.json)/g,"")
        if(item.isDirectory == false && fileNameCheck(item.name, fileName)){
            item.jsonList.forEach(msg => {
                console.log(msg.text);

                $("#fileContent").append(msg.text + "\n");
            })
        }
    })
}

const fileNameCheck = function(filename1, filename2){
    return filename1.replace(/([0-9]|-|.json|\/)/g,"") == filename2;
}