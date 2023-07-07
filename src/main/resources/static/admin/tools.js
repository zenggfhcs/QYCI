function login_check() {
    let bool = false
    $.ajax({
        url: "/admin/check",
        type: "POST",
        data: '', // 注意这里，传递对象给后台，这里必须将对象进行序列化
        async: false,
        dataType: 'json',
        cache: false,
        processData: false,
        contentType: 'application/json', // 注意这里，传递对象给后台，这里必须是 application/json
        success: res => {
            bool = res
        },
        error: err => {
            console.log('err', err)
        }
    })
    return bool
}