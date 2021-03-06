const { ApolloServer } = require('apollo-server')
const _ = require('lodash')
const queries = require('./typedefs-resolvers/_queries')
const mutations = require('./typedefs-resolvers/_mutations')
const equipments = require('./typedefs-resolvers/equipments')
const supplies = require('./typedefs-resolvers/supplies')
const teams = require('./typedefs-resolvers/_teams')
const enums = require('./typedefs-resolvers/_enums')
const givens = require('./typedefs-resolvers/givens')
const softwares = require('./typedefs-resolvers/softwares')
const tools = require('./typedefs-resolvers/tools')
const people = require('./typedefs-resolvers/people')


const typeDefs = [
    queries,
    mutations,
    equipments.typeDefs,
    teams.typeDefs,
    supplies.typeDefs,
    enums,
    givens.typeDefs,
    tools.typeDefs,
    softwares.typeDefs,
    people.typeDefs
]
const resolvers = [
    equipments.resolvers,
    teams.resolvers,
    supplies.resolvers,
    givens.resolvers,
    tools.resolvers,
    softwares.resolvers,
    people.resolvers
]

// server.applyMiddleware({ app, path: '/some-custom-path' });

const server = new ApolloServer({ typeDefs, resolvers })

server.listen().then(({ url }) => {
    console.log(`🚀  Server ready at ${url}`)
})