$(function(){
    listSort();
    $("#userBtn").on("click", usersConsole);
    $("#channelBtn").on("click", channelsConsole);
    $("#getContentBtn").on("click", getFileContent);
});

let users = [];
let channels = [];

const usersConsole = function(){
    clearContent();
    
    list.forEach(item => {
        if(item.name == "users.json"){
            item.jsonList.forEach(user => {
                if(!user.is_bot){
                    users.push(user);
                    appendContent(user.profile.real_name + "(" + user.id + ")");
                }
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

    dailyChannels();
}

const getFileContent = function(){
    clearContent();
    var fileName = $("#channelName").val();
    
    list.forEach(item => {
        // "test/2020-03-03.json".replace(/(test\/|.json)/g,"")
        if(item.isDirectory == false && fileNameCheck(item.name, fileName)){
            item.jsonList.forEach(msg => {
                let chat = "[" + new Date(msg.ts*1000) + "] : " + msg.text;
                // console.log( "[" + msg.user_profile.real_name +  "] : " + msg.text);
                console.log(chat);

                appendContent(chat);
            })
        }
    })
}

let dailyChannelList = [];

const dailyChannels = function(){
    list.forEach(item => {
        if(item.isDirectory == true){
            dailyChannelList.push(item);
        }
    })

    console.log(dailyChannelList);
}

const fileNameCheck = function(filename1, filename2){
    // return filename1.replace(/([0-9]|-|.json|\/)/g,"") == filename2;
    return filename1.replace(/\/(.*)/,"") == filename2;
}

const appendContent = function(content){
    $("#fileContent").append(content + "\n");
}

const clearContent = function(){
    $("#fileContent").html("");
}

const listSort = function(){
    list.sort((a, b) => a.name.localeCompare(b.name));

    console.log(list);
}