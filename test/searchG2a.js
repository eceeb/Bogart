var sinon     = require('sinon')
var assert    = require('assert')
var should    = require('should')
var emitter   = require('../libs/emitter')
var searchG2a = require('../libs/searchG2a')


describe('searchG2a', function() {

    var row = {}
    var website = '<body><div class="selected-price">34,99 €</div><body>'

    beforeEach(function() {
        spy = sinon.spy()
        emitter.on('foundRow', spy)
    })

    describe('#after()', function () {
        it('should be positive when price is smaller on website', function () {
            row.seek = '40'
            searchG2a.after(row, website)
            spy.called.should.be.true()
        })
    })

    describe('#after()', function () {
        it('should be positive when price is the same on website', function () {
            row.seek = '34,99'
            searchG2a.after(row, website)
            spy.called.should.be.true()
      })
    })

    describe('#after()', function () {
        it('should be negative when price is greater on website', function () {
            row.seek = '30.50'
            searchG2a.after(row, website)
            spy.called.should.be.false()
        })
    })
})
