$(function() {
    $('#password-check').change(function() {
        $('#password').attr('type', $(this).is(':checked') ? 'text' : 'password');
    });

    $('#submit').submit(function() {
        if ($('#password').val().trim().length < 8) {
            $('#password-info').html('パスワードは 8 文字以上で入力してください。');
        } else {
            $('#password-info').html('');
        }

        if ($('#password-info').html() === '') {
            $.ajax({
                type: 'POST',
                url: '/pass/reset',
                data: 'token=' + $('#token').val().trim() +
                    '&email=' + $('#email').val().trim() +
                    '&password=' + $('#password').val().trim()
            }).done(function(data) {
                $().toastmessage('showToast', {
                    text: 'パスワードの再設定に成功しました！<br />ログインページに移動します！',
                    position: 'top-center',
                    type: 'success',
                    stayTime: 3000,
                    close: function() {
                        // location.href = '/';
                    }
                });
            }).fail(function(data) {
                $().toastmessage('showToast', {
                    text: 'パスワードの再設定に失敗しました... エラー: ' + (data.message ? data.message : '不明なエラー'),
                    position: 'top-center',
                    type: 'error'
                });
            });
        }

        return false;
    });
});
