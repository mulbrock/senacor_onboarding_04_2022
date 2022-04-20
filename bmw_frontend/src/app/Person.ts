export interface Person {
  id: number,
  firstName: string,
  lastName: string,
  age: number
}
export class Person {
  constructor(
    public firstName: string,
    public lastName: string,
    public age: number
  ) {}
}
