// FIXME: "4" should *not* be hard-coded.  use 'data-' property instead
$(function() {
//    var minimumPasswordLength = 4;
//    var keyToName = {
//        'uname': 'ユーザー名',
//        'email': 'メールアドレス',
//        'password': 'パスワード'
//    };
//    var errorMessages = {
//        'uname': 'その' + keyToName['uname'] + 'は既に使われています。',
//        'email': 'その' + keyToName['email'] + 'は既に使われています。',
//        'password': keyToName['password'] + 'が短すぎます。'
//    };
//
//    _.each(['uname', 'email'], function(key) {
//        var inputId = '#' + key;
//        var spanId = '#' + key + '-info';
//
//        $(inputId).bind('blur', function() {
//            $(spanId).html('<img src="/assets/images/loading.gif" />');
//            $.getJSON('/api/exist/' + key + '/' + $(this).val().trim(), function(json) {
//                $(spanId).html(json.exist ? errorMessages[key] : '');
//            });
//        });
//    });

    function validateUname() {
        if (!$('#uname').val().match(/^[\w\d][\w\d-]*$/)) {
            $('#uname-info').html('ユーザー名は英数字と"_"と"-"のみ使用可能です。また"-"は先頭には使えません。');
            return false;
        }

        $('#uname-info').html('<img src="/assets/images/loading.gif" />');
        $.getJSON('/api/exist/uname/' + $('#uname').val().trim(), function(json) {
            if (json.exist) {
                $('#uname-info').html('そのユーザー名はすでに使われています。');
                return false;
            }
            $('#uname-info').html('');
            $('#uname-check').html('<img src="/assets/images/success.png" />');
            console.debug('こっち');
            return true;
        });
    }

    $('#uname').bind('blur', function() {
        validateUname();
    });

    $('#email').bind('blur', function() {
        //
    });

    $('#submit').submit(function() {
//        var errorList = [];
//        if (!$('#uname').val().match(/^[\w\d][\w\d-]*$/)) {
//            errorList.push(keyToName['uname'] + 'は英数字と"_"と"-"のみ使用可能です。また"-"は先頭には使えません。');
//        }
//        if ($('#email').val().search('@') === -1) {
//            errorList.push('メールアドレスの形式が不正です。');
//        }
//        if ($('#password').val().trim().length < minimumPasswordLength) {
//            errorList.push('パスワードは ' + minimumPasswordLength + ' 文字以上で入力してください。');
//        }
        $.when(validateUname()).done(function(v1) {
            console.debug('あち ' + v1);
        });

        return false;
    });

});
