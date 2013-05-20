$(function() {
    $('#manage-list > li > a').click(function () {
        var type = $(this).data('type');
        $('#code-div').show();
        $('#submit-div').show();

        var targetText = '';
        var purpose = '';
        switch (type) {
        case 'list-css-edit':
            targetText = '(一覧ページの CSS 編集)';
            break;
        case 'page-css-edit':
            targetText = '(個別記事ページの CSS 編集)';
            break;
        case 'list-js-edit':
            targetText = '(一覧ページの JavaScript 編集)';
            break;
        case 'page-js-edit':
            targetText = '(個別記事ページの JavaScript 編集)';
            break;
        default:
            break;
        }
        $('#code-target').text(targetText);
        $('#code-target').data('type', type);

        $('#code-content').attr('disabled', 'disabled');
        var apiUrl = '/api' +
            (type.contains('css')  ? '/css' : '/js') +
            (type.contains('list') ? '/list' : '/page') +
            '/' + $('#uname').val();
            
        $.ajax({
            url: apiUrl
        }).done(function(data) {
            $('#code-content').val(data['code']);
        }).always(function(data) {
            $('#code-content').removeAttr('disabled');
        });
    });

    $('#submit').submit(function() {
        var type = $('#code-target').data('type');

        $.ajax({
            type: 'POST',
            url: '/api/css-js',
            data: {
                token: $('#token').val(),
                purpose: type.contains('list') ? 'list' : 'page',
                contentType: (type.contains('css') ? 'css' : 'js'),
                content: $('#code-content').val()
            }
        }).done(function(data) {
            $().toastmessage('showToast', {
                text: (type.contains('css') ? 'CSS' : 'JavaScript') +
                    ' の保存に成功しました！',
                position: 'top-center',
                type: 'success',
                stayTime: 3000,
                close: function() {}
            });
        }).fail(function(data) {
            $().toastmessage('showToast', {
                text: (type.contains('css') ? 'CSS' : 'JavaScript') +
                    ' の保存に失敗しました: ' +
                    (data.message ? data.message : '不明なエラー'),
                position: 'top-center',
                type: 'error'
            });
        });

        return false;
    });
});
