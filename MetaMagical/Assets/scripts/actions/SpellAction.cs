using System;
using MagicEngine;

public interface SpellAction
{
	void perform(MagicEntity entity, Spell spell);
}
