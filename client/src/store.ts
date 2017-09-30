import { Action, GlobalStore } from 'fluxx'

import { Person } from './models'

export type MainState = {
  people: Array<Person>
}

const intialState: MainState = {
  people: []
}

export const updatePeople = Action<Array<Person>>("updatePeople")

export default GlobalStore(intialState, on => {
  on(updatePeople, (state, people) => ({...state, people}))
})