import * as ReactDOM from 'react-dom'
import * as React from 'react'

import MainLayout from './main'
import { people } from './api'
import MainState, { updatePeople } from './store'

people().then(updatePeople)

const app = document.getElementById('app')

const main = React.createElement(MainLayout)

ReactDOM.render(
  main, 
  app
)