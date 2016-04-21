var nodemailer  = require('nodemailer')
var transporter = nodemailer.createTransport(process.env.EMAIL_URI)


var mail = function () {

    return {

        send : function (row) {
           
            var mailOptions = {
                to      : row.email, 
                text    : 'Checkout ' + row.url,
                subject : 'Hurray found something...',
                from    : '"Humphrey Notification" <humphrey.notify@hotmail.com>'
            }

            transporter.sendMail(mailOptions, function(error, info) {
                if (error)
                    console.log('Fatal mailing error: ' + error)
                else
                    console.log(info)
            })
        }
    }
}()

module.exports = mail
