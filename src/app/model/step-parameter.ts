export class StepParameter {
  id: number;
  name: string;
  value: string;

  constructor(name?: string) {
    if (name) {
      this.name = name;
    }
  }
}
