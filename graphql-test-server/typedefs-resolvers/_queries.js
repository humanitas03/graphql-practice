const { gql } = require('apollo-server')

const typeDefs = gql`
    type Query {
        teams: [Team]
        team(id: Int): Team
        equipments: [Equipment]
        supplies: [Supply]
    }
`

module.exports = typeDefs