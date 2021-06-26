const { gql } = require('apollo-server')

const typeDefs = gql`
    type Mutation {
        insertEquipment(
            id: ID,
            used_by: String,
            count: Int,
            new_or_used: String
        ): Equipment
        editEquipment(
            id: ID,
            userd_by: String,
            count: Int,
            new_or_used: String
        ):Equipment
        deleteEquipment(id: ID): Equipment
        
        postPerson(personInput: PostPersonInput): People!
    }
`


module.exports = typeDefs