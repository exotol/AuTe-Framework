export class StepParameter {
  name: string;
  value: string;

  constructor(name?: string) {
    if (name) {
      this.name = name;
    }
  }
}
