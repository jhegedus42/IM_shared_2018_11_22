## What is this code ?

A humble attempt at writing a 'full-stack' end-to-end 'Scala-only' Single-page-app "Hello-World web-framework" 
with the following properties: 
- Server :
  - Event Sourcing
  - Persistence
    - achieved by journaling all CRUD events
    
- Client 
    - React
    - Single page application
    - URL router
    - State is NOT stored using
      - FLUX
      - DIODE 
      - REDUX
    - State is stored in a Cache 
        - simple and transparent
        - same (relational) data representation as on server
          - relational in the sense : 
             - both normalized and denormalized representation of the domain model is possible
             - references : "primary keys", "foreign keys"
             - joins, etc ...
          - data is contained in `case classes`
          
        - can contain both normalized ("Entities") and denormalized data (views)

- Domain model
    - Normalized data representation (relational model)
    - Views that can be denormalized 
     
- Communication
  - type-safe RPC calls (poor man's [servant](https://haskell-servant.github.io/) for Scala) 
    - implemented using type classes
    - type-safe RPC call interface is available to client and server
  - the same data structures (types) can be used in the source code for both 
    - the client
    - the server
  - this is possible because : part of the source code is compiled into both
    - `JVM bytecode`
    - `Javascript` 
  - Entities, Views, RPC calls are transmitted as `case classes` encoded into `JSON`
  
