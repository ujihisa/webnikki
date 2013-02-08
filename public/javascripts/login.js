$(function() {
    $('#submit').submit(function() {
        $.ajax({
            type: 'POST',
            url: '/login',
            data: { email: $('#email').val().trim(), password: $('#password').val() }
        }).done(function(data) {
            if (data.success) {
                $().toastmessage('showToast', {
                    text: 'ログインに成功しました！<br />トップページに移動します！',
                    position: 'top-center',
                    type: 'success',
                    stayTime: 3000,
                    close: function() { location.href = '/'; }
                });
            } else {
                $().toastmessage('showToast', {
                    text: 'ログインに失敗しました...。メールアドレスかパスワードを間違えています。',
                    position: 'top-center',
                    type: 'error',
                    stayTime: 2000
                });
            }
        }).fail(function(data) {
            // shouldn't come here
            alert('エラー: ' + (data.message ? data.message : '不明なエラー'));
        });

        return false;
    });
});
