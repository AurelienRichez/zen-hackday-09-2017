import {fetchJson} from './protocol'
import { Person } from './models'

export const people = () => fetchJson<Array<Person>>('/people')