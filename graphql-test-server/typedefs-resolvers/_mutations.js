const { gql } = require('apollo-server')

const typeDefs = gql`
    type Mutation {
        insertEquipment(
            id: String,
            used_by: String,
            count: Int,
            new_or_used: String
        ): Equipment
        editEquipment(
            id: String,
            userd_by: String,
            count: Int,
            new_or_used: String
        ):Equipment
        deleteEquipment(id: String): Equipment
        
        postPerson(input: PostPersonInput): People!
    }
`


module.exports = typeDefs