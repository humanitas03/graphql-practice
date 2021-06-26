const { gql } = require('apollo-server')

const typeDefs = gql`
    type Query {
        teams: [Team]
        team(id: Int): Team
        equipments: [Equipment]
        supplies: [Supply]
        equipmentAdvs: [EquipmentAdv]
        givens: [Given] # 유니언
        softwares: [Software]
        software: Software
        people: [People]
    }
`

module.exports = typeDefs