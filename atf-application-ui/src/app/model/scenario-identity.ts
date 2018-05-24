export class ScenarioIdentity {
  projectCode: string;
  group: string;
  code: string;

  constructor(projectCode: string, group: string, code: string) {
    this.projectCode = projectCode;
    this.group = group;
    this.code = code;
  }
}
