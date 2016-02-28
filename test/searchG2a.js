var should    = require('should')
var searchG2a = require('../libs/search/searchG2a')


describe('searchG2a', function() {

    var website = '<body><div class="selected-price">34,99 €</div><body>'

    describe('#after()', function () {
        it('should be positive when price is smaller on website', function () {
            seek = '40'
            searchG2a.after(seek, website).should.be.true()
        })
    })

    describe('#after()', function () {
        it('should be positive when price is the same on website', function () {
            seek = '34,99'
            searchG2a.after(seek, website).should.be.true()
      })
    })

    describe('#after()', function () {
        it('should be negative when price is greater on website', function () {
            seek = '30.50'
            searchG2a.after(seek, website).should.be.false()
        })
    })
})
