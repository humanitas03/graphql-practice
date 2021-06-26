const { gql } = require('apollo-server')
const dbWorks = require('../dbWorks')


const typeDefs = gql`
    type Team {
        id: ID!
        manager: String
        office: String
        extension_number: String
        mascot: String
        cleaning_duty: String
        project: String
        supplies: [Supply]
    }
`

const resolvers = {
    Query:{
        teams: (parent, args) => dbWorks.getTeams(args),
    },
}

module.exports = {
    typeDefs: typeDefs,
    resolvers: resolvers
}