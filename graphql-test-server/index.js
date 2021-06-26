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
    count: Int
    new_or_used: String
  }
  type Supply {
    id: String
    team: Int
  }
  
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

    },

    Mutation: {
        insertEquipment: (parent, args, context, info) => {
            database.equipments.push(args)
            return args
        },
        editEquipment: (parent, args, context, info) => {
            return database.equipments.filter((equipment)=>{
                return equipment.id===args.id
            }).map((equipment)=>{
                Object.assign(equipment, args)
                return equipment
            })[0]
        },
        deleteEquipment: (parent, args, context, info) => {
            const deleted = database.equipments
                .filter((equipment) => {
                    return equipment.id === args.id
                })[0]
            database.equipments = database.equipments
                .filter((equipment) => {
                    return equipment.id !== args.id
                })
            return deleted
        }
    }
}

// server.applyMiddleware({ app, path: '/some-custom-path' });

const server = new ApolloServer({ typeDefs, resolvers })

server.listen().then(({ url }) => {
    console.log(`🚀  Server ready at ${url}`)
})