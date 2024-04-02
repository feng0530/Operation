$("#loginBtn").on("click", function(){
    let req = "users/login";
    let user = $("#username").val();
    let pasw = $("#password").val();

    let req_body = {
        username: user,
        password: pasw
    }

    fetch(req, {
        method: 'post',
        headers: { 'content-type': 'application/json' },
        body: JSON.stringify(req_body)
    }).then(response => {
        return response.json();
    }).then(res => {
        if(res.code === 400){
            alert(res.result);
            return
        }
        
        localStorage.setItem('accessToken', res.result.accessToken);
        localStorage.setItem('refreshToken', res.result.refreshToken);
        window.location.href = "crud.html";
    })

})