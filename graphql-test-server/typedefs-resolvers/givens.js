const { gql } = require('apollo-server')
const dbWorks = require('../dbWorks.js')

// Union 으로
// Given은 Equipment를 받을 수도 있고, Supply도 받을 수 잇다.
const typeDefs = gql`
    union Given = Equipment | Supply
`

const resolvers = {
    Query: {
        givens: (parent, args) => {
            return [
                ...dbWorks.getEquipments(args),
                ...dbWorks.getSupplies(args)
            ]
        }
    },
    Given: {
        __resolveType(given, context, info) {
            if (given.used_by) {
                return 'Equipment'
            }
            if (given.team) {
                return 'Supply'
            }
            return null
        }
    }
}
module.exports = {
    typeDefs: typeDefs,
    resolvers: resolvers
}