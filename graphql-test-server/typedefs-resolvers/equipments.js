const { gql } = require('apollo-server')
const dbWorks = require('../dbWorks')

const typeDefs = gql`
    type Equipment implements Tool {
        id: ID!
        used_by: Role!
        count: Int
        new_or_used: NewOrUsed!
    }    
    # !는 not_null을 의미합니다.
    type EquipmentAdv {
        id: ID!
        used_by: String!
        count: Int!
        use_rate:Float
        is_new:Boolean!
        # 리스트 타입
        users: [String!]
    }
`

const resolvers = {
    Query:{
        equipments: (parent, args) => dbWorks.getEquipments(args),
        equipmentAdvs: (parent, args) => dbWorks.getEquipments(args)
            .map((equipment)=>{
                //장비의 used_by가 'developer인 경우 useRate를 2로 고정한다
                if(equipment.used_by==='developer') {
                    equipment.use_rate = Math.random().toFixed(2)
                }
                equipment.is_new = equipment.new_or_used === 'new'
                if(Math.random() > 0.5) {
                    equipment.users = []
                    dbWorks.getPeople(args).forEach((person) => {
                        if(person.role===equipment.used_by && Math.random() < 0.2) {
                            equipment.users.push(person.last_name)
                        }
                    })
                }
                return equipment
            }),
        // softwares: (parent, args) => dbWorks.getSoftwares(args)
    },
    Mutation :{
        deleteEquipment: (parent, args) => dbWorks.deleteItem('equipments', args),
        insertEquipment: (parent, args, context, info) => dbWorks.postEquipment(args),
        editEquipment: (parent, args, context, info) => dbWorks.editEquipment(args)
    }

}

module.exports = {
    typeDefs: typeDefs,
    resolvers: resolvers
}