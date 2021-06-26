const { gql } = require('apollo-server')

const typeDefs = gql`
    type Query {
        teams: [Team]
        team(id: Int): Team
        equipments: [Equipment]
        supplies: [Supply]
        equipmentAdvs: [EquipmentAdv]
        givens: [Given] # 유니언
        softwares: [Software] # 인터페이스
    }
`

module.exports = typeDefs