var mandrill = require('mandrill-api/mandrill')
var client   = new mandrill.Mandrill(process.env.MANDRILL_APIKEY)


var mail = function () {

    return {

        send : function (row) {

            var content = 'Found: ' + row.seek + ' on ' + row.url

            var message = {
                'to'           : [row.email],
                'async'       : false,
                'ip_pool'       : 'Main Pool',
                'from_name'   : 'Search-That-Site',
                'from_email'  : 'noreply@Search-That-Site.com',
                'raw_message' : 'Subject: Found something\n\n' + content,
            }

            client.messages.sendRaw(message, function (result) {
                    console.log(result)
                }, function(e) {
                    console.log('Fatal error could not send mailing!');
            });
        }
    }
}()

module.exports = mail
