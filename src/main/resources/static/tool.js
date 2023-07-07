function sead_email(email) {// 异步发送验证码
    const user = {
        email: email
    }
    $.ajax({
        url: '/send/email-code',
        type: 'POST',
        data: JSON.stringify(user), // 注意这里，传递对象给后台，这里必须将对象进行序列化
        async: true,
        dataType: 'json',
        cache: false,
        processData: false,
        contentType: 'application/json', // 注意这里，传递对象给后台，这里必须是 application/json
        success: res => {
            console.log('res', res)
        },
        error: err => {
            console.log('err', err)
        }
    })
}

function check_login() {// 已登录是 false，未登录是 true
    let bool = false
    $.ajax({
        url: "/login-check",
        type: "POST",
        data: '', // 注意这里，传递对象给后台，这里必须将对象进行序列化
        async: false,
        dataType: 'json',
        cache: false,
        processData: false,
        contentType: 'application/json', // 注意这里，传递对象给后台，这里必须是 application/json
        success: res => {
            bool = (res === 0)
        },
        error: err => {
            console.log('err', err)
        }
    })
    return bool
}