package net.skrymer.agent.validation;

import javax.validation.constraints.NotBlank;

public class Name {
  @NotBlank
  private String first;
  private String middle;
  @NotBlank
  private String last;

  public void setLast(String last) {
    this.last = last;
  }

  public String getLast() {
    return last;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getFirst() {
    return first;
  }

  public void setMiddle(String middle) {
    this.middle = middle;
  }

  public String getMiddle() {
    return middle;
  }
}
