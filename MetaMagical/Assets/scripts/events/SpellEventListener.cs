using System;
using MagicEngine;

namespace AssemblyCSharp
{
	public interface SpellEventListener
	{
		void HandleEvent(SpellEventType type, MagicEntity entity);
	}
}

