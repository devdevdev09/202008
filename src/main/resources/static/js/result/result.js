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
        //    console.log("test : ");
        //    console.log(item.jsonList);
           item.jsonList.forEach(user => {
               users.push(user);
            //    console.log("name : " + element.name);
            //    console.log("real_name : " + element.real_name);
            //    console.log("profile.real_name : " + element.profile.real_name);
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
                // console.log(channel.id + " : " + channel.name + " : " + channel.topic);
                // console.log(channel.members);
            });
        }
    });
    console.log(channels);
}

const getFileContent = function(){
    var fileName = $("#channelName").val();
    list.forEach(item => {
        // "test/2020-03-03.json".replace(/(test\/|.json)/g,"")
        // isDirectory == false
        // 
        if(item.isDirectory == false && item.name.replace(/([0-9]|-|.json|\/)/g,"") == fileName){
            item.jsonList.forEach(msg => {
                console.log(msg.text);
            })
        }
    })
}