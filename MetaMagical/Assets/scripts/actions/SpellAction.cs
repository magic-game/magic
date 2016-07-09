using System;
using MagicEngine;

public interface SpellAction
{
	MagicEntity perform(MagicEntity entity);
	SpellEventType getEvent();
}
