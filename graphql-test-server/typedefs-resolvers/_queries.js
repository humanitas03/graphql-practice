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
        #필터 적용
        peopleFiltered(
            team: Int,
            sex: Sex,
            blood_type: BloodType,
            from: String
        ): [People]
        
        #페이징
        peoplePaginated(
            page: Int!,
            per_page: Int!
        ): [People]
    }
`

module.exports = typeDefs