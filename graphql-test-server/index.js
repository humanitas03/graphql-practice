const database = require('./database')
const { ApolloServer, gql } = require('apollo-server')

// 쿼리 루트타입 정의.
const typeDefs = gql`
  type Query {
    teams: [Team]
    team(id: Int): Team
    equipments: [Equipment]
    supplies: [Supply]
  }
  type Team {
    id: Int
    manager: String
    office: String
    extension_number: String
    mascot: String
    cleaning_duty: String
    project: String
    supplies: [Supply]  
  }
  type Equipment {
    id: String
    used_by: String
    new_or_used: String
  }
  type Supply {
    id: String
    team: Int
  }
`

//리졸버
const resolvers = {
    Query: {
        teams: () => database.teams
            .map((team)=>{
                team.supplies = database.supplies.filter((supply)=>{
                    return supply.team === team.id
                })
                return team
            }),
        team: (parent, args, context, info) => database.teams.filter((team)=>{
            return team.id === args.id
        })[0],
        equipments: () => database.equipments,
        supplies: () => database.supplies

    }
}

// server.applyMiddleware({ app, path: '/some-custom-path' });

const server = new ApolloServer({ typeDefs, resolvers })

server.listen().then(({ url }) => {
    console.log(`🚀  Server ready at ${url}`)
})