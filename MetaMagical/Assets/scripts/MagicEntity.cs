using System;
using UnityEngine;

namespace MagicEngine
{
	public interface MagicEntity
	{
		Transform getTransform();
		Rigidbody getRigidbody();
	}
}
