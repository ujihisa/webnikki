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
            $('#code-content').html(data['code']);
        }).always(function(data) {
            $('#code-content').removeAttr('disabled');
        });
    });

    $('#submit').submit(function() {
        var type = $('#code-target').data('type');

        $.ajax({
            type: 'POST',
            url: '/api/' + (type.contains('css') ? 'css' : 'js'),
            data: {
                token: $('#token').val(),
                purpose: type.contains('list') ? 'list' : 'page',
                text: $('#code-content').val()
            }
        }).done(function(data) {
            console.debug('done', data);
        }).fail(function(data) {
            console.debug('fail', data);
        });

        return false;
    });
});
