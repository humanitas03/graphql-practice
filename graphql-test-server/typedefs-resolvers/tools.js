// 인터페이스 : 유사한 객체 타입을 만들기 위한 공통 필드 타입.

const { gql } = require('apollo-server')
const typeDefs = gql`
    interface Tool {
        id: ID!
        used_by: Role!
    }
`
const resolvers = {
    Tool: {
        __resolveType(tool, context, info) {
            if (tool.developed_by) {
                return 'Software'
            }
            if (tool.new_or_used) {
                return 'Equipment'
            }
            return null
        }
    }
}
module.exports = {
    typeDefs: typeDefs,
    resolvers: resolvers
}