using System;
using UnityEngine;
using AssemblyCSharp;

namespace MagicEngine
{
	public interface MagicEntity
	{
		Transform getTransform();
		Rigidbody getRigidbody();
		void AddEventListener(SpellEventListener listener);
		void takeDamage(int amount);
	}
}
