$(function() {
    $('#manage-list > li > a').click(function () {
        // var type = $(this).data('type');
        // $.ajax({
        //     url: '/api/'
        // }).done(function(data) {
        //     console.debug(data);
        // });

        var type = $(this).data('type');
        $('#code-div').show();
        $('#submit-div').show();

        var targetText = '';
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
    });
});
