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
        // TODO: AJAX Loading
    });

    $('#submit').submit(function() {
        var type = $('#code-target').data('type');
        // console.debug('code-content', $('#code-content').val());

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
