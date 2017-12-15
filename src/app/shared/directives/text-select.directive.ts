import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[appTextSelect]',
})
export class TextSelectDirective {

  @HostListener('keydown', ['$event']) overrideSelectAllShortcut(e) {
    if (e.ctrlKey && e.key === 'a') {
      e.preventDefault();
      window.getSelection().selectAllChildren(this.ref.nativeElement);
    }
  }

  constructor(private ref: ElementRef) {
    this.ref.nativeElement.tabIndex = 0;
  }
}
