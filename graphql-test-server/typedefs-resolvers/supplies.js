const { gql } = require('apollo-server')
const dbWorks = require('../dbWorks')

const typeDefs = gql`
    type Supply {
        id: String
        team: Int
    }
`

const resolvers = {
    Query:{
        supplies: (parent, args) => dbWorks.getSupplies(args),
    },
}

module.exports = {
    typeDefs: typeDefs,
    resolvers: resolvers
}