// let add_btn = document.querySelector("#add");
// let select_btn = document.querySelector("#select");
// let update_btn = document.querySelector("#update");
// let delete_btn = document.querySelector("#delete");
let result_tbody = document.querySelector("tbody");

buttons = [
    { btn: "#add", function: "addCustomer()" },
    { btn: "#select", function: "getCustomerList()" },
    { btn: "#update", function: "updateCustomer()" },
    { btn: "#delete", function: "deleteCustomer()" },
    { btn: "#logout", function: "logout()" }
]

buttons.forEach(button => {
    let bt_el = $(button.btn);
    bt_el.on("click", function() {
        eval(button.function);
    });

});

function addCustomer() {
    let req = config.SERVER_IP + 'customer/add';
    let conditions = [
        { key: "name", selector: "#createPage #name" },
        { key: "phone", selector: "#createPage #phone" },
        { key: "location", selector: "#createPage #location" }
    ];

    let req_body = {
        name: "",
        phone: "",
        location: ""
    }

    conditions.forEach(condition => {
        let value = $(condition.selector).val();
        if (value != null && value !== "") {
            req_body[condition.key] = value;
        }
    });

    if(!logined()){
            return;
    }

    fetch(req, {
        method: 'post',
        headers: { 
            'content-type': 'application/json',
            'Authorization' : 'Bearer ' + localStorage.getItem("accessToken"),
            'refreshToken' : 'Bearer ' + localStorage.getItem('refreshToken')
        },
        body: JSON.stringify(req_body)
    }).then(response => {
        refreshToken(response);
        return response.json();
    }).then(res => {
        if(reLogin(res.message)){
            return;
        }
        alert(res.message);
    })
}

function getCustomerList() {
    let req = config.SERVER_IP + 'customer/list?';
    let conditions = [
        { key: "id", selector: "#readPage #id" },
        { key: "name", selector: "#readPage #name" },
        { key: "phone", selector: "#readPage #phone" },
        { key: "location", selector: "#readPage #location" }
    ];

    conditions.forEach(condition => {
        let value = $(condition.selector).val();
        if (value != null && value !== "") {
            req += `${condition.key}=${value}&`;
        }
    });
    // 刪除最後一個 "&" 字符
    req = req.slice(0, -1);

    if(!logined()){
            return;
    }

    fetch(req, {
        method: 'Get',
        headers: { 
            'content-type': 'application/json',
            'Authorization' : 'Bearer ' + localStorage.getItem("accessToken"),
            'refreshToken' : 'Bearer ' + localStorage.getItem('refreshToken')
        }
    }).then(response => {
        refreshToken(response);
        return response.json();
    }).then(res => {
        if(reLogin(res.message)){
            return;
        }

        if(res.result !== null){
            result_tbody.innerHTML = '';
            for (const customer of res.result) {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                `;

                tr.children[0].textContent = customer.id;
                tr.children[1].textContent = customer.name;
                tr.children[2].textContent = customer.phone;
                tr.children[3].textContent = customer.location;
                result_tbody.appendChild(tr);
            }
        }else{
            result_tbody.innerHTML = '';
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td colspan="4" style="text-align: center">尚無相關資訊</td>
            `;
            result_tbody.appendChild(tr);
        }
    })
}

function updateCustomer() {
    let req = config.SERVER_IP + 'customer/update';
    let conditions = [
        { key: "id", selector: "#updatePage #id" },
        { key: "name", selector: "#updatePage #name" },
        { key: "phone", selector: "#updatePage #phone" },
        { key: "location", selector: "#updatePage #location" }
    ];

    let req_body = {
        id: "",
        name: "",
        phone: "",
        location: ""
    }

    conditions.forEach(condition => {
        let value = $(condition.selector).val();
        if (value != null && value !== "") {
            req_body[condition.key] = value;
        }
    });

    if(!logined()){
            return;
    }

    fetch(req, {
        method: 'PUT',
        headers: { 
            'content-type': 'application/json',
            'Authorization' : 'Bearer ' + localStorage.getItem("accessToken"),
            'refreshToken' : 'Bearer ' + localStorage.getItem('refreshToken')
        },
        body: JSON.stringify(req_body)
    }).then(response => {
        refreshToken(response);
        return response.json();
    }).then(res => {
        if(reLogin(res.message)){
            return;
        }
        alert(res.message);
    })
}

function deleteCustomer() {
    let req = config.SERVER_IP + 'customer/delete?';
    let conditions = [
        { key: "id", selector: "#deletePage #id" },
        { key: "name", selector: "#deletePage #name" },
        { key: "phone", selector: "#deletePage #phone" },
        { key: "location", selector: "#deletePage #location" }
    ];

    let req_body = {
        id: "",
        name: "",
        phone: "",
        location: ""
    }

    conditions.forEach(condition => {
        let value = $(condition.selector).val();
        if (value != null && value !== "") {
            req += `${condition.key}=${value}&`;
        }
    });
    // 刪除最後一個 "&" 字符
    req = req.slice(0, -1);

    if(!logined()){
        return;
    }

    fetch(req, {
        method: 'DELETE',
        headers: { 
            'content-type': 'application/json',
            'Authorization' : 'Bearer ' + localStorage.getItem("accessToken"),
            'refreshToken' : 'Bearer ' + localStorage.getItem('refreshToken')
        }
    }).then(response => {
        refreshToken(response);
        return response.json();
    }).then(res => {
        if(reLogin(res.message)){
            return;
        }
        alert(res.message);
    })
}

// 新增的函数，用于上传 CSV 文件
function uploadCsv() {
    let req = config.SERVER_IP + 'csv/upload';
    let file = $('#csvFile')[0].files[0];

    if(!logined()){
        return;
    }

    if (file) {
        let formData = new FormData();
        formData.append('file', file);
        formData.append('name', file.name);

        // 顯示加載畫面，目前當回應速度太快，會來不及隱藏!!!!!!!!!!!!!!!!!!!!!
        $('#loadingModal').show();

        fetch(req, {
            method: 'post',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("accessToken"),
                'refreshToken': 'Bearer ' + localStorage.getItem('refreshToken')
            },
            body: formData
        }).then(response => {
            refreshToken(response);
            $('#loadingModal').hide();
            return response.json();
        }).then(res => {

            if(reLogin(res.message)){
                return;
            }
            
            setTimeout(() => {
                alert(res.result);
            }, 100);
        })

    } else {
        alert("Please select a CSV file.");
    }
}

function logined() {
    let accessToken = localStorage.getItem("accessToken");
    let refreshToken = localStorage.getItem('refreshToken');

    if(accessToken === null || refreshToken === null) {
        alert("尚未登入!");
        window.location.href = "login.html";
        return false;
    }
    return true;
}

function logout() {
    
    if(!logined()) {
        return;
    }
    
    if(confirm("確定要登出嗎?")){
        removeToken();
        window.location.href = "login.html";
    }
}

function reLogin(message) {

    if(message === "請重新登入!"){
        alert(message);
        removeToken();
        window.location.href = "login.html";
        return true;
    }
    return false;
}

function refreshToken(response) {
    let accessToken = response.headers.get('Accesstoken');
    let refreshToken = response.headers.get('Refreshtoken');

    if(accessToken !== null && refreshToken !== null){
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
    }
}

function removeToken(){
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
}