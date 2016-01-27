var sinon   = require('sinon')
var assert  = require('assert')
var should  = require('should')
var search  = require('../libs/search')
var emitter = require('../libs/emitter')


describe('search', function() {

    var row = {}
    var website = '<body>searchword<body>'

    beforeEach(function() {
        spy = sinon.spy()
        emitter.on('foundRow', spy)
    })

    describe('#after()', function () {
        it('should be positive when search term was found on website', function () {
            row.seek = 'searchword'
            search.after(row, website)
            spy.called.should.be.true()
        })
    })

    describe('#after()', function () {
        it('should be positive when search term was found on website', function () {
            row.seek = 'searchword'
            search.after(row, website)
            spy.called.should.be.true()
        })
    })

    describe('#after()', function () {
        it('should be negative when search term was not found on website', function () {
            row.seek = 'Kirk'
            search.after(row, website)
            spy.called.should.be.false()
        })
    })
})
