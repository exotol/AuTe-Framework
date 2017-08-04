import {Component, forwardRef, Input, OnInit} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => InputNullComponent),
  multi: true
};

@Component({
  selector: 'app-input-null',
  templateUrl: './input-null.component.html',
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class InputNullComponent implements OnInit, ControlValueAccessor  {

  innerValue: String;
  @Input()
  type = 'input';
  @Input()
  addButtonText = 'Add';
  @Input()
  removeButtonText = 'Clear';

  private onTouchedCallback: () => void = () => { };
  private onChangeCallback: (_: any) => void = () => { };

  get value(): any {
    return this.innerValue;
  };

  set value(v: any) {
    if (v !== this.innerValue) {
      this.innerValue = v;
      this.onChangeCallback(v);
    }
  }

  constructor() { }

  ngOnInit() {
  }

  onBlur() {
    this.onTouchedCallback();
  }

  writeValue(obj: any): void {
    if (obj !== this.innerValue) {
      this.innerValue = obj;
    }
  }

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

  setNull() {
    if (this.value === '' || confirm('Confirm clear')) {
      this.value = null;
    }
  }

  setNotNull() {
    this.value = '';
  }

}
