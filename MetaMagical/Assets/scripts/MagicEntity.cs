using System;
using UnityEngine;
using AssemblyCSharp;

namespace MagicEngine
{
	public interface MagicEntity
	{
		Place getPlace();
		Rigidbody getRigidbody();
		void AddEventListener(SpellEventListener listener);
		void takeDamage(int amount);
	}
}
