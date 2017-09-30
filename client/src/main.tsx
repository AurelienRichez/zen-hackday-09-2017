import * as React from 'react'
import connect from 'fluxx/lib/ReactConnector'

import { Person } from './models'
import MainStore, {MainState} from './store'

interface MainProps {
  people: Array<Person>
}

class MainView extends React.Component<MainProps>{

  render(){
    return (
      <div>
        {this.props.people.map((person, index) =>
          <div key={index}>
            <span>{person.name} {person.lastName}</span>
            <span> : </span>
            <span>{person.tags.map(t => `${t},`)}</span>
          </div>
        )}
      </div>
    )
  }
}

export default connect(MainView, MainStore, (mainState: MainState) => ({people: mainState.people}))

