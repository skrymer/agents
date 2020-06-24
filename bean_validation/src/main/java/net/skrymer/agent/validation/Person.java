package net.skrymer.agent.validation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Person {
  private Name name;

  @Validate
  public void setName(@NotNull @Valid Name name) {
    this.name = name;
  }
}
