$(function() {
    $('#manage-list > li > a').click(function () {
        // switch ($(this).data('type')) {
        // case 'list-css-edit':
        //     console.debug('list-css-edit');
        //     break;
        // case 'page-css-edit':
        //     console.debug('page-css-edit');
        //     break;
        // case 'list-js-edit':
        //     console.debug('list-js-edit');
        //     break;
        // case 'page-js-edit':
        //     console.debug('page-js-edit');
        //     break;
        // default:
        //     break;
        // }
        var type = $(this).data('type');
        $.ajax({
            url: '/api/'
        }).done(function(data) {
            console.debug(data);
        });
    });
});
